using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace BuddyAPI.Models
{
	public class Pinpoints
	{

		[Key]
		[DatabaseGenerated(DatabaseGeneratedOption.Identity)]
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
