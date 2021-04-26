using System;
using System.Collections.Generic;

namespace BuddyAPI.Models
{
	public class UserRoles
	{
		public int userId { get; set; }
		public int roleId { get; set; }

		public virtual ICollection<UserRoles> userRoles { get; set; }
	}
}
