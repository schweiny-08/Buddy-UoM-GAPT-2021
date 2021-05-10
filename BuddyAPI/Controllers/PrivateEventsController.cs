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
        public async Task<ActionResult<PrivateEvents>> PostEvents([Bind("Pinpoint_Id,PrivateEventName,PrivateEventDescription,StartTime,EndTime")] PrivateEvents privateEvents)
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

            //Retreive a list of events which match the pinpoint event
            IEnumerable<PrivateEvents> pinpointEvent = GetEventByPinpoint(privateEvents.Pinpoint_Id);

            foreach (PrivateEvents item in pinpointEvent)
            {
                //Checker to not allow cross events at the same pinpoints whilst allowing to be placed in between
                if (item.EndTime > privateEvents.StartTime && item.StartTime < privateEvents.EndTime)
                {
                    throw new ArgumentException("Cannot create event, starttime and endtime fall under and already pre-existing event!");
                }
            }

            _context.PrivateEvents.Add(privateEvents);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetPrivateEvents", new { id = privateEvents.PrivateEvent_Id }, privateEvents);
        }

        [HttpGet("GetPrivateEventByPinpoint")]
        private IEnumerable<PrivateEvents> GetEventByPinpoint(int pinpointId)
        {
            return _context.PrivateEvents.Where(e => e.Pinpoint_Id == pinpointId);

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
