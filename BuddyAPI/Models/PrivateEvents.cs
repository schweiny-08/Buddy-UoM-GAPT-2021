using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;


namespace BuddyAPI.Models
{
	public class PrivateEvents
	{
		[Key]
		[DatabaseGenerated(DatabaseGeneratedOption.Identity)]
		public int PrivateEvent_Id { get; set; }
		public int Pinpoint_Id { get; set; }
		public string PrivateEventName { get; set; }
		public string PrivateEventDescription { get; set; }
		public DateTime StartTime { get; set; }
		public DateTime EndTime { get; set; }

		//public virtual ICollection<PrivateEvents> privateEvents { get; set; }
	}
}
