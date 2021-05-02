using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;


namespace BuddyAPI.Models
{
	public class Events
	{
		[Key]
		[DatabaseGenerated(DatabaseGeneratedOption.Identity)]
		public int Event_Id { get; set; }
		public int Pinpoint_Id { get; set; }
		public string EventName { get; set; }
		public string EventDescription { get; set; }
		public DateTime StartTime { get; set; }
		public DateTime EndTime { get; set; }

		//public virtual ICollection<Events> events { get; set; }
	}
}
