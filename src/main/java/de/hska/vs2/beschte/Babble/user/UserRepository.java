package de.hska.vs2.beschte.Babble.user;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Repository;

import de.hska.vs2.beschte.Babble.post.Post;

@Repository
public class UserRepository {
	private static final String KEY_FOR_ALL_USERS = "all:users";
	private static final String KEY_FOR_ALL_POSTS = "all:posts";
	private static final String USER_PREFIX = "user:";
	private static final String USER_SUFFIX = ":user";
	private static final String POSTS_PREFIX = "posts:";
	private static final String POSTS_SUFFIX = ":posts";
	private static final String FOLLOWER_SUFFIX = ":follower";
	private static final String FOLLOWING_SUFFIX = ":following";

	/**
	 * to generate unique ids for user
	 */
	private RedisAtomicLong userid;

	/**
	 * to generate unique ids for user
	 */
	private RedisAtomicLong postid;

	/**
	 * to save data in String format
	 */
	private StringRedisTemplate stringRedisTemplate;

	/**
	 * hash operations for stringRedisTemplate
	 */
	private HashOperations<String, String, String> redisStringHashOps;

	private SetOperations<String, String> redisStringSetOps;
	private ZSetOperations<String, String> redisStringSortedSetOps;

	private ListOperations<String, String> redisStringListOps;

	private ValueOperations<String, String> redisStringValueOps;

	@Autowired
	public UserRepository(StringRedisTemplate stringRedisTemplate) {
		this.stringRedisTemplate = stringRedisTemplate;
		this.userid = new RedisAtomicLong("userid", stringRedisTemplate.getConnectionFactory());
		this.postid = new RedisAtomicLong("postid", stringRedisTemplate.getConnectionFactory());
	}

	@PostConstruct
	private void init() {
		redisStringHashOps = stringRedisTemplate.opsForHash();
		redisStringSetOps = stringRedisTemplate.opsForSet();
		redisStringSortedSetOps = stringRedisTemplate.opsForZSet();
		redisStringListOps = stringRedisTemplate.opsForList();
		redisStringValueOps = stringRedisTemplate.opsForValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * hska.iwi.vslab.repo.UserRepository#saveUser(hska.iwi.vslab.model.User)
	 */
	public void saveUser(User user) {
		// generate a unique id
		String id = String.valueOf(userid.incrementAndGet());
		user.setId(id);

		// to show how objects can be saved
		// be careful, if username already exists it's not added another time
		String key = USER_PREFIX + user.getUsername();
		redisStringHashOps.put(key, "id", id);
		redisStringHashOps.put(key, "firstname", user.getFirstname());
		redisStringHashOps.put(key, "lastname", user.getLastname());
		redisStringHashOps.put(key, "username", user.getUsername());
		redisStringHashOps.put(key, "password", user.getPassword());

		redisStringSetOps.add(KEY_FOR_ALL_USERS, user.getUsername());
	}

	public User findAndCreateUser(String username) {
		User user = new User();
		String key = USER_PREFIX + username;

		if (redisStringSetOps.isMember(KEY_FOR_ALL_USERS, username)) {
			user.setId(redisStringHashOps.get(key, "id"));
			user.setFirstname(redisStringHashOps.get(key, "firstname"));
			user.setLastname(redisStringHashOps.get(key, "lastname"));
			user.setUsername(redisStringHashOps.get(key, "username"));
			user.setPassword(redisStringHashOps.get(key, "password"));
		} else
			user = null;
		return user;
	}

	public Set<String> findFollowerIDs(String username) {
		Set<String> follower = new HashSet<>();
		String followersKey = USER_PREFIX + username + FOLLOWER_SUFFIX;

		if (redisStringSetOps.isMember(KEY_FOR_ALL_USERS, username) && 0 != redisStringSetOps.size(followersKey)) {
			follower.addAll(redisStringSetOps.members(followersKey));
		}

		return follower;
	}

	public Set<String> findFollowingIDs(String username) {
		Set<String> following = new HashSet<>();
		String followingKey = USER_PREFIX + username + FOLLOWING_SUFFIX;

		if (redisStringSetOps.isMember(KEY_FOR_ALL_USERS, username) && 0 != redisStringSetOps.size(followingKey)) {
			following.addAll(redisStringSetOps.members(followingKey));
		}

		return following;
	}

	public void follow(String usernameFollower, String usernameFollowing) {
		String followerKey = USER_PREFIX + usernameFollower + FOLLOWING_SUFFIX;
		String followingKey = USER_PREFIX + usernameFollowing + FOLLOWER_SUFFIX;

		redisStringSetOps.add(followerKey, usernameFollowing);
		redisStringSetOps.add(followingKey, usernameFollower);
	}

	public List<String> findPostsIDsForUser(String username) {
		List<String> posts = new LinkedList<>();
		String userPostsKey = USER_PREFIX + username + POSTS_SUFFIX;

		if (redisStringSetOps.isMember(KEY_FOR_ALL_USERS, username) && 0 != redisStringListOps.size(userPostsKey)) {
			posts.addAll(redisStringSetOps.members(userPostsKey));
		}

		return posts;
	}

	public List<Post> findGlobalPostsInRange(long start, long end) {
		List<Post> posts = new LinkedList<>();
		Set<String> postIDs = redisStringSortedSetOps.range(KEY_FOR_ALL_POSTS, start, end);
		for (String id : postIDs) {
			posts.add(findAndCreatePost(id));
		}
		return posts;
	}

	public void savePost(Post post, String username) {
		// generate a unique id
		String id = String.valueOf(postid.incrementAndGet());
		post.setId(id);

		// to show how objects can be saved
		// be careful, if username already exists it's not added another time
		String key = POSTS_PREFIX + id;
		redisStringHashOps.put(key, "id", id);
		redisStringHashOps.put(key, "content", post.getContent());
		redisStringHashOps.put(key, "timestamp", String.valueOf(post.getTimestamp().getTime()));

		redisStringSortedSetOps.add(KEY_FOR_ALL_POSTS, id, post.getTimestamp().getTime());

		String userPostsKey = USER_PREFIX + username + POSTS_SUFFIX;
		redisStringListOps.rightPush(userPostsKey, id);

		String postToUserKey = POSTS_PREFIX + id + USER_SUFFIX;
		redisStringValueOps.append(postToUserKey, username);
	}

	private Post findAndCreatePost(String postId) {
		Post post = new Post();
		String key = POSTS_PREFIX + postId;

		post.setId(redisStringHashOps.get(key, "id"));
		post.setContent(redisStringHashOps.get(key, "content"));
		post.setTimestamp(new Date(Long.valueOf(redisStringHashOps.get(key, "timestamp"))));
		
		if (null == post.getContent() || post.getContent().isEmpty())
			return null;
		
		return post;
	}
	
	public User findAndCreateUserForPost(String postId){
		String postToUserKey = POSTS_PREFIX + postId + USER_SUFFIX;
		String username = redisStringValueOps.get(postToUserKey);
		return findAndCreateUser(username);
	}
}