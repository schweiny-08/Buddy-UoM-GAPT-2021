using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace BuddyAPI.Models
{
	public class PinpointTypes
	{
		[Key]
		[DatabaseGenerated(DatabaseGeneratedOption.Identity)]
		public int pinpointType_Id { get; set; }

		[Required]
		[StringLength(50, ErrorMessage = "Pinpoint type name cannot be longer than 50 characters.")]
		public string pinpointTypeName{ get; set; }

		public string pinpointIcon { get; set; }

	}
}
