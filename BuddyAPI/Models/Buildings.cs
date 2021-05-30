using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace BuddyAPI.Models
{
	public class Buildings
{
		[Key]
		public int building_Id { get; set; }
		[Required]
		public int user_Id{ get; set; }
		[Required]
		public double latitude { get; set; }
		[Required]
		public double longitude { get; set; }
		[Required]
		[StringLength(50, ErrorMessage ="Building name cannot be longer than 50 characters.")]
		public string buildingName { get; set; }
		[StringLength(150, ErrorMessage = "Desciption cannot be longer than 150 characters.")]
		public string buildingDescription { get; set; }

	}
}
