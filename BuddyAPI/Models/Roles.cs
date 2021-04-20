using System;

public class Roles
{
	public Roles()
	{
		public int roleId { get; set; }
		public string roleType { get; set; }

		public virtual ICollection<Roles> Role { get; set; }
	}
}
