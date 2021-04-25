﻿using System;
using System.Collections.Generic;

namespace BuddyAPI
{
	public class PrivateEvents
	{
		public int privEventId { get; set; }
		public int pinpointId { get; set; }
		public string privEventName { get; set; }
		public string privEventDesc { get; set; }
		public DateTime startTime { get; set; }
		public DateTime endTime { get; set; }

		public virtual ICollection<PrivateEvents> privateEvents { get; set; }
	}
}
