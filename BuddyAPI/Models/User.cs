using System;

public class User
{
	public User()
	{
		public int userId { get; set; }
		public string username { get; set; }
		public int telephone { get; set; }
		public string email { get; set; }
		public string password { get; set; }

		public virtual ICollection<User> user { get; set; }
	}
}
