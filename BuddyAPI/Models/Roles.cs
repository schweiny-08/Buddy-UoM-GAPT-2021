using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Runtime.Serialization;
using System.Text.Json.Serialization;

namespace BuddyAPI.Models
{
	public class Roles
	{

        //public Roles()
        //{
        //    Users = new HashSet<User>();

        //}
        [Key]
        //[DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int Role_Id { get; set; }
		public string RoleType { get; set; }

        //[System.Text.Json.Serialization.JsonIgnore]
        //[JsonIgnore]
        //[IgnoreDataMember]
        //public virtual ICollection<User> Users { get; set; }
    }
}
