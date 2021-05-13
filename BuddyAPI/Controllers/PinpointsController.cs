using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using BuddyAPI.Data;
using BuddyAPI.Models;
using BuddyAPI.ViewModels;

namespace BuddyAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class PinpointsController : ControllerBase
    {
        private readonly BuddyAPIContext _context;

        public PinpointsController(BuddyAPIContext context)
        {
            _context = context;
        }

        // GET: api/Pinpoints
        [HttpGet("getAllPinpoints")]
        public async Task<ActionResult<IEnumerable<Pinpoints>>> GetPinpoints()
        {
            return await _context.Pinpoints.ToListAsync();
        }

        // GET: api/Pinpoints/5
        [HttpGet("getPinpointById")]
        public async Task<ActionResult<Pinpoints>> GetPinpoints(int id)
        {
            var pinpoints = await _context.Pinpoints.FindAsync(id);

            if (pinpoints == null)
            {
                return NotFound();
            }

            return pinpoints;
        }

        [HttpGet("getPinpointByName")]
        public async Task<ActionResult<Pinpoints>> GetPinpointsByName(string name)
        {
            var pinpoints = _context.Pinpoints.FirstOrDefault(p => p.pinpointName == name);

            if (pinpoints == null)
            {
                return NotFound();
            }

            return pinpoints;
        }

        // PUT: api/Pinpoints/5
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPut("editPinpointById")]
        public async Task<IActionResult> PutPinpoints(int id, Pinpoints pinpoints)
        {
            if (id != pinpoints.pinpoint_Id)
            {
                return BadRequest();
            }

            _context.Entry(pinpoints).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!PinpointsExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return NoContent();
        }

        // POST: api/Pinpoints
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost("addPinpoint")]
        public async Task<ActionResult<Pinpoints>> PostPinpoints(Pinpoints pinpoints)
        {
            _context.Pinpoints.Add(pinpoints);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetPinpoints", new { id = pinpoints.pinpoint_Id }, pinpoints);
        }

        // DELETE: api/Pinpoints/5
        [HttpDelete("deletePinpointById")]
        public async Task<IActionResult> DeletePinpoints(int id)
        {
            var pinpoints = await _context.Pinpoints.FindAsync(id);
            if (pinpoints == null)
            {
                return NotFound();
            }

            _context.Pinpoints.Remove(pinpoints);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        private bool PinpointsExists(int id)
        {
            return _context.Pinpoints.Any(e => e.pinpoint_Id == id);
        }

        [HttpPost("SetSessionLocation")]
        public void SetLocation(string key , Pinpoints pinpoint)
        {
            HttpContext.Session.SetObjectAsJson(key, pinpoint);
        }
        
        [HttpGet("GetSessionLocation")]
        public Pinpoints GetLocation(string key)
        {
            Pinpoints  pinpoint =  HttpContext.Session.GetObjectFromJson<Pinpoints>(key);
            return pinpoint;
        }
        
      /*  [HttpPost("SetEndPoint")]
        public void SetEndPoint(Pinpoints pinpoint)
        {
            HttpContext.Session.SetObjectAsJson("EndPoint", pinpoint);
        }
        
        [HttpGet("GetEndPoint")]
        public Pinpoints GetEndPoint()
        {
            return HttpContext.Session.GetObjectFromJson<Pinpoints>("EndPoint");
        }*/
    }
}
