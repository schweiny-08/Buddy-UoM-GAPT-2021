using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace BuddyAPI.Models
{
	public class Roles
	{
		[Key]
		[DatabaseGenerated(DatabaseGeneratedOption.Identity)]
		public int Role_Id { get; set; }
		public string RoleType { get; set; }

		//public virtual ICollection<Roles> roles { get; set; }
	}
}
