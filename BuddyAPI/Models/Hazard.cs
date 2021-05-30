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
		[StringLength(50, ErrorMessage = "Hazard type must not be longer than 50 characters.")]
		public string hazardType { get; set; }

	}
}
