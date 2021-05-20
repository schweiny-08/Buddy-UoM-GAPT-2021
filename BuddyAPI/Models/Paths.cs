using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace BuddyAPI.Models
{
    public class Paths
    {
        [Key]
        public int pinpoint_Id { get; set; }
        [Key]
        public int pinpoint_Id_2 { get; set; }
    }
}
