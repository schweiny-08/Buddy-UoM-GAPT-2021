using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace BuddyAPI.Models
{
	public class Pinpoints
	{

		[Key]
		public int pinpoint_Id { get; set; }

		[Required]
		public int pinpointType_Id { get; set; }

		[Required]
		public int floor_Id { get; set; }

		[Required]
		public int location_Id { get; set; }

		public int hazard_Id { get; set; }

		[Required]
		public string pinpointName { get; set; }

		[Required]
		public string pinpointDescription { get; set; }

		//public virtual ICollection<Pinpoints> pinpoints { get; set; }
	}
}
