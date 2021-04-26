using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace BuddyAPI.Models
{
	public class Roles
	{
		[Key]
		public int Role_Id { get; set; }
		public string RoleType { get; set; }

		//public virtual ICollection<Roles> roles { get; set; }
	}
}
