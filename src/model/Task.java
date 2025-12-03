package model;

public class Task {
	private final String content;
	
	public Task(String content) {
		this.content = content.trim();
	}
	
	public String getContent() {
		return content;
	}

	@Override
	public int hashCode() {
		return content.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null || getClass() != obj.getClass())
			return false;
		Task task = (Task) obj;
		return content.equals(task.content);
	}
}
