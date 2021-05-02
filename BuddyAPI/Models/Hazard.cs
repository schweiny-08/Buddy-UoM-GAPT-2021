using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace BuddyAPI.Models
{
	public class Hazard
	{
		[Key]
		public int hazard_Id { get; set; }
		[Required]
		public string hazardType { get; set; }

		//public virtual ICollection<Hazards> hazards { get; set; }
	}
}
