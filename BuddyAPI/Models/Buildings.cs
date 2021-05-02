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
		public string buildingName { get; set; }
		public string buildingDescription { get; set; }

		//public virtual ICollection<Buildings> buildings { get; set; }
	}
}
