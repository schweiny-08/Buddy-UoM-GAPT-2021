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
    public class PrivateEventsController : ControllerBase
    {
        private readonly BuddyAPIContext _context;

        public PrivateEventsController(BuddyAPIContext context)
        {
            _context = context;
        }

        // GET: api/PrivateEvents
        [HttpGet("getAllPrivateEvents")]
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
            HttpContext.Session.SessionExists("UserLoggedIn");
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



        // POST: api/Events
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost("addPrivateEvent")]
        public async Task<ActionResult<PrivateEvents>> PostPrivateEvent([Bind("Pinpoint_Id,PrivateEventName,PrivateEventDescription,StartTime,EndTime")] PrivateEvents privateEvents)
        {
            HttpContext.Session.SessionExists("UserLoggedIn");

            //Validations
            //Check if Pinpoint is not null
            if (privateEvents.Pinpoint_Id == 0)
            {
                throw new ArgumentException("Error, no pinpoint was selected");
            }
            //Check if EventName are not null
            if (privateEvents.PrivateEventName.Equals("string") || privateEvents.PrivateEventName.Equals(""))
            {
                throw new ArgumentException("Please input an Event Name");
            }
            //Check Start Time is not before EndTime
            if (privateEvents.StartTime >= privateEvents.EndTime)
            {
                throw new ArgumentException("Error End Time cannot be before Start Time");
            }

            _context.PrivateEvents.Add(privateEvents);
            await _context.SaveChangesAsync();

            privateEvents = _context.PrivateEvents.OrderBy(pe => pe.PrivateEvent_Id).Last();
            Itineraries itineraries = new Itineraries();
            itineraries.PrivateEvent_Id = privateEvents.PrivateEvent_Id;
            User user = HttpContext.Session.GetObjectFromJson<User>("UserLoggedIn");
            itineraries.User_Id = user.User_Id;

            ItinerariesController itinerariesController = new ItinerariesController(_context);
            await itinerariesController.PostItineraries(itineraries);
            return Ok("Private Event has been created and added to your itinerary");
        }

        private IEnumerable<PrivateEvents> GetEventByPinpoint(int pinpointId)
        {
            return _context.PrivateEvents.Where(e => e.Pinpoint_Id == pinpointId);

        }

        private IEnumerable<Itineraries> GetEventFromItineraries(int userid)
        {
            return _context.Itineraries.Where(u => u.User_Id == userid);
        }

        // DELETE: api/PrivateEvents/5
        [HttpDelete("deletePrivateEventById")]
        public async Task<IActionResult> DeletePrivateEvents(int id)
        {
            HttpContext.Session.SessionExists("UserLoggedIn");
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
