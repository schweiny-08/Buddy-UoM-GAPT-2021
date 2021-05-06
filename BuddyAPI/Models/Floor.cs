using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace BuddyAPI.Models
{
	public class Floor
	{
		[Key]
		[DatabaseGenerated(DatabaseGeneratedOption.Identity)]
		public int floor_Id { get; set; }
		[Required]
		public int floorLevel { get; set; }
		[Required]
		public int building_Id { get; set; }
		[Required]
		[StringLength(50, ErrorMessage = "Floor name cannot be longer than 50 characters.")]
		public string floorName { get; set; }
		[StringLength(150, ErrorMessage = "Description cannot be longer than 150 characters.")]
		public string floorDescription { get; set; }
		public string floorImage { get; set; }

		//public virtual ICollection<Floor> floors { get; set; }
	}
}
