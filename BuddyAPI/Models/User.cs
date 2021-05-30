using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;
//using System.Text.Json.Serialization;
using Newtonsoft.Json;

#nullable disable

namespace BuddyAPI.Models
{
	public class User
	{
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int User_Id { get; set; }
		public string Username { get; set; }                               
		public int Telephone { get; set; }
		public string Email { get; set; }
		public string Password { get; set; }

		[Required]
		public int Role_Id { get; set; }

    }
}
