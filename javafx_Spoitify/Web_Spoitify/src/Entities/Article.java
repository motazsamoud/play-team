package Entities;

public class Article {

	private int id;
    private int userId;
    private String title;
    private String content;
    private String like;
    
    private int likeCount;

    public void incrementLikeCount() {
        likeCount++;
    }

    public int getLikeCount() {
        return likeCount;
    }
    public Article(int id, int userId, String title, String content, String like) {
		this.id = id;
		this.userId = userId;
		this.title = title;
		this.content = content;
		this.like = like;
	}
    
    public Article(int userId, String title, String content, String like) {
		super();
		this.userId = userId;
		this.title = title;
		this.content = content;
		this.like = like;
	}

	public Article(String title, String content, String like) {
		super();
		this.title = title;
		this.content = content;
		this.like = like;
	}

	public Article() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
	public String toString() {
		return "Article [id=" + id + ", userId=" + userId + ", title=" + title + ", content=" + content + ", like="
				+ like + ", likeCount=" + likeCount + "]";
	}

	public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }
}

