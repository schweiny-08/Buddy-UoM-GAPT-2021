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
		public string pinpointTypeName{ get; set; }

		public string pinpointIcon { get; set; }

		//public virtual ICollection<PinpointTypes> pinpointTypes { get; set; }
	}
}
