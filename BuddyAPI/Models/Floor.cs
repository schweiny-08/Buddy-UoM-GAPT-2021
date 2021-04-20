using System;
using System.Collections.Generic;

namespace BuddyAPI
{
	public class Floor
	{
		public int floorId { get; set; }
		public int buildingId { get; set; }
		public string floorName { get; set; }
		public string floorDesc { get; set; }
		public string floorImage { get; set; }

		public virtual ICollection<Floor> floor { get; set; }
	}
}
