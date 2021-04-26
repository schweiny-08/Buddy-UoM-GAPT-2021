using System;
using System.Collections.Generic;

namespace BuddyAPI.Models
{
	public class Locations
	{
		public int locationId { get; set; }
		public int longitude { get; set; }
		public int latitude { get; set; }


		public virtual ICollection<Locations> locations { get; set; }
	}
}
