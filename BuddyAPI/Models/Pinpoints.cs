using System;
using System.Collections.Generic;

namespace BuddyAPI
{
	public class Pinpoints
	{
		public int pinpointId { get; set; }
		public int pinTypeId { get; set; }
		public int floorId { get; set; }
		public int locationId { get; set; }
		public int hazardId { get; set; }
		public string pinpointName { get; set; }
		public string pinpointDesc { get; set; }

		public virtual ICollection<Pinpoints> pinpoints { get; set; }
	}
}
