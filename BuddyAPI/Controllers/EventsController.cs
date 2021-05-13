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

        [HttpGet("getEventsByTimeFrame")]
        public async Task<ActionResult<IEnumerable<Events>>> GetEvents(DateTime start, DateTime end) {
            var events = await _context.Events.Where(e => e.StartTime >= start && e.StartTime <= end || e.EndTime >= start && e.EndTime <= end).ToListAsync();

            if (events == null)
                return NotFound();

            return events;
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
            AdminPrivileges();

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
        public async Task<ActionResult<Events>> PostEvents([Bind("Pinpoint_Id,EventName,EventDescription,StartTime,EndTime")] Events events)
        {
            AdminPrivileges();

            //Validations
            //Check if Pinpoint is not null
            if (events.Pinpoint_Id  == 0)
            {
                throw new ArgumentException("Error, no pinpoint was selected");
            }
            //Check if EventName are not null
            if(events.EventName.Equals("string") || events.EventName.Equals(""))
            {
                throw new ArgumentException("Please input an Event Name");
            }
            //Check Start Time is not before EndTime
            if(events.StartTime >= events.EndTime)
            {
                throw new ArgumentException("Error End Time cannot be before Start Time");
            }

            //Retreive a list of events which match the pinpoint event
            IEnumerable<Events> pinpointEvent = GetEventByPinpoint(events.Pinpoint_Id);

            foreach(Events item in pinpointEvent)
            {
                //Checker to not allow cross events at the same pinpoints whilst allowing to be placed in between
                if (item.EndTime > events.StartTime && item.StartTime < events.EndTime) { 
                    throw new ArgumentException("Cannot create event, starttime and endtime fall under and already pre-existing event!");
                }
            }

            _context.Events.Add(events);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetEvents", new { id = events.Event_Id }, events);
        }

        [HttpGet ("GetEventByPinpoint")]
        private IEnumerable<Events> GetEventByPinpoint(int pinpointId)
        {
            return _context.Events.Where(e => e.Pinpoint_Id == pinpointId);

        }


        // DELETE: api/Events/5
        [HttpDelete("deleteEventById")]
        public async Task<IActionResult> DeleteEvents(int id)
        {
            AdminPrivileges();
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

        //Checks if current user logged in has Admin / Architect Priveleges
        private void AdminPrivileges()
        {
            HttpContext.Session.SessionExists("UserLoggedIn");
            User currentUser = HttpContext.Session.GetObjectFromJson<User>("UserLoggedIn");
            RolesController rc = new RolesController(_context);
            Task<ActionResult<Roles>> role = rc.GetRoles(currentUser.Role_Id);
            if (role.Result.Value.RoleType.Equals("Viewer"))
            {
                throw new ArgumentException("Current User is not an Architect");
            }

        }
    }
}
