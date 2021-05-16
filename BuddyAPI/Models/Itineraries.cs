using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;


namespace BuddyAPI.Models
{
	public class Itineraries
	{
		[Key]
		[DatabaseGenerated(DatabaseGeneratedOption.Identity)]
		public int Itinerary_Id { get; set; }
		public int User_Id { get; set; }
		public int Event_Id { get; set; }
		public int PrivateEvent_Id { get; set; }
        //public Itineraries itineraries { get; internal set; }

        //public virtual ICollection<Itineraries> itineraries { get; set; }
    }
}
