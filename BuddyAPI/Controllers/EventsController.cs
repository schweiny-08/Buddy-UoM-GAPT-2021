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
    public class EventsController : ControllerBase
    {
        private readonly BuddyAPIContext _context;

        public EventsController(BuddyAPIContext context)
        {
            _context = context;
        }

        // GET: api/Events
        [HttpGet("getAllEvents")]
        public async Task<ActionResult<IEnumerable<Events>>> GetEvents()
        {
            return await _context.Events.ToListAsync();
        }

        // GET: api/Events/5
        [HttpGet("getEventById")]
        public async Task<ActionResult<Events>> GetEvents(int id)
        {
            var events = await _context.Events.FindAsync(id);

            if (events == null)
            {
                return NotFound();
            }

            return events;
        }

        // PUT: api/Events/5
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPut("editEventById")]
        public async Task<IActionResult> PutEvents(int id, Events events)
        {
            if (id != events.Event_Id)
            {
                return BadRequest();
            }

            _context.Entry(events).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!EventsExists(id))
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

        // POST: api/Events
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost("addEvent")]
        public async Task<ActionResult<Events>> PostEvents(Events events)
        {
            _context.Events.Add(events);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetEvents", new { id = events.Event_Id }, events);
        }

        // DELETE: api/Events/5
        [HttpDelete("deleteEventById")]
        public async Task<IActionResult> DeleteEvents(int id)
        {
            var events = await _context.Events.FindAsync(id);
            if (events == null)
            {
                return NotFound();
            }

            _context.Events.Remove(events);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        private bool EventsExists(int id)
        {
            return _context.Events.Any(e => e.Event_Id == id);
        }
    }
}
