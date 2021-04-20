using System;

public class Itineraries
{
	public Itineraries()
	{
		public int itineraryId { get; set; }
		public int userId { get; set; }
		public int eventId { get; set; }
		public int privEventId { get; set; }

		public virtual ICollection<Itineraries> Itineraries { get; set; }
	}
}
