using System;
using System.Collections.Generic;

namespace BuddyAPI
{
	public class Events
	{
		public int eventId { get; set; }
		public int pinpointId { get; set; }
		public string eventName { get; set; }
		public string eventDesc { get; set; }
		public DateTime startTime { get; set; }
		public DateTime endTime { get; set; }

		public virtual ICollection<Events> events { get; set; }
	}
}
