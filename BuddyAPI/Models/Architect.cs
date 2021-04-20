using System;
using System.Collections.Generic;

namespace BuddyAPI
{
	public class Architect
	{
		public int architectId { get; set; }
		public int userId { get; set; }

		public virtual ICollection<Architect> architects { get; set; }
	}
}
