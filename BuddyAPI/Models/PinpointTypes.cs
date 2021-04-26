using System;
using System.Collections.Generic;

namespace BuddyAPI.Models
{
	public class PinpointTypes
	{
		public int pinTypeId { get; set; }
		public int pinpointName { get; set; }
		public string pinpointIcon { get; set; }

		public virtual ICollection<PinpointTypes> pinpointTypes { get; set; }
	}
}
