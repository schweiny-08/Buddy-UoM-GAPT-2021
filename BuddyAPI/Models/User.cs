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
		//[Required]
		public string Username { get; set; }                               
		//[Required]
		public int Telephone { get; set; }
		//[Required]
		public string Email { get; set; }
		//[Required]
		public string Password { get; set; }

		[Required]
		//[JsonIgnore]
		public int Role_Id { get; set; }

        //[ForeignKey("Role_Id")]
        //[JsonIgnore]
        //public virtual Roles Roles { get; set; }


    }
}
