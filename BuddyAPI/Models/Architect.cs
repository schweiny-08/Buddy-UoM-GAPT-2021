using System;
using System.Collections.Generic;

namespace BuddyAPI.Models
{
	public class Architect
	{
		public int architectId { get; set; }
		public int userId { get; set; }

		public virtual ICollection<Architect> architects { get; set; }
	}
}

