using System;
using System.Collections.Generic;

namespace BuddyAPI
{
	public class Itineraries
	{
		public int itineraryId { get; set; }
		public int userId { get; set; }
		public int eventId { get; set; }
		public int privEventId { get; set; }

		public virtual ICollection<Itineraries> itineraries { get; set; }
	}
}
