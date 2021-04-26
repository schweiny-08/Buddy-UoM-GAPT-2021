﻿using System;
using System.Collections.Generic;

namespace BuddyAPI.Models
{
	public class Hazards
	{
		public int hazardId { get; set; }
		public string hazardType { get; set; }

		public virtual ICollection<Hazards> hazards { get; set; }
	}
}