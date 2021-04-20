using System;
using System.Collections.Generic;

namespace BuddyAPI
{
	public class Roles
	{
		public int roleId { get; set; }
		public string roleType { get; set; }

		public virtual ICollection<Roles> roles { get; set; }
	}
}
