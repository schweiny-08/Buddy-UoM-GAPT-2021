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
		public string floorName { get; set; }
		public string floorDescription { get; set; }
		public string floorImage { get; set; }

		//public virtual ICollection<Floor> floors { get; set; }
	}
}
