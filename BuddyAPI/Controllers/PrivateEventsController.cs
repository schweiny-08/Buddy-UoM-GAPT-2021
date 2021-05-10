using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using BuddyAPI.Data;
using BuddyAPI.Models;

namespace BuddyAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class PrivateEventsController : ControllerBase
    {
        private readonly BuddyAPIContext _context;

        public PrivateEventsController(BuddyAPIContext context)
        {
            _context = context;
        }

        // GET: api/PrivateEvents
        [HttpGet ("getAllPrivateEvents")]
        public async Task<ActionResult<IEnumerable<PrivateEvents>>> GetPrivateEvents()
        {
            return await _context.PrivateEvents.ToListAsync();
        }

        // GET: api/PrivateEvents/5
        [HttpGet("getPrivateEventById")]
        public async Task<ActionResult<PrivateEvents>> GetPrivateEvents(int id)
        {
            var privateEvents = await _context.PrivateEvents.FindAsync(id);

            if (privateEvents == null)
            {
                return NotFound();
            }

            return privateEvents;
        }

        // PUT: api/PrivateEvents/5
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPut("editPrivateEventById")]
        public async Task<IActionResult> PutPrivateEvents(int id, PrivateEvents privateEvents)
        {
            if (id != privateEvents.PrivateEvent_Id)
            {
                return BadRequest();
            }

            _context.Entry(privateEvents).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!PrivateEventsExists(id))
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

        // POST: api/PrivateEvents
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost("addPrivateEvent")]
        public async Task<ActionResult<PrivateEvents>> PostPrivateEvents(PrivateEvents privateEvents)
        {
            _context.PrivateEvents.Add(privateEvents);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetPrivateEvents", new { id = privateEvents.PrivateEvent_Id }, privateEvents);
        }

        // DELETE: api/PrivateEvents/5
        [HttpDelete("deletePrivateEventById")]
        public async Task<IActionResult> DeletePrivateEvents(int id)
        {
            var privateEvents = await _context.PrivateEvents.FindAsync(id);
            if (privateEvents == null)
            {
                return NotFound();
            }

            _context.PrivateEvents.Remove(privateEvents);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        private bool PrivateEventsExists(int id)
        {
            return _context.PrivateEvents.Any(e => e.PrivateEvent_Id == id);
        }
    }
}
