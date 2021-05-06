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
		public double latitude { get; set; }

		[Required]
		public double longitude { get; set; }

		public int hazard_Id { get; set; }

		[Required]
		[StringLength(50, ErrorMessage = "Pinpoint name must be shorter than 50 characters.")]
		public string pinpointName { get; set; }
		[StringLength(150, ErrorMessage = "Pinpoint description must be shorter than 150 characters.")]
		public string pinpointDescription { get; set; }

		//public virtual ICollection<Pinpoints> pinpoints { get; set; }
	}
}
