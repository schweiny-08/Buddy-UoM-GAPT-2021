using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Runtime.Serialization;
using System.Text.Json.Serialization;


#nullable disable

namespace BuddyAPI.Models
{
	public partial class Roles
	{

        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int Role_Id { get; set; }
		public string RoleType { get; set; }

    }
}
