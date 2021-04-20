using System;

public class UserRoles
{
	public UserRoles()
	{
		public int userId { get; set; }
		public int roleId { get; set; }

		public virtual ICollection<UserRoles> userRole { get; set; }
	}
}
