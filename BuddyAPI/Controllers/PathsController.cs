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
    public class PathsController : ControllerBase
    {
        private readonly BuddyAPIContext _context;

        public PathsController(BuddyAPIContext context)
        {
            _context = context;
        }

        // GET: api/Paths
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Paths>>> GetPaths()
        {
            return await _context.Paths.ToListAsync();
        }

        // GET: api/Paths/5
        [HttpGet("{id}")]
        public async Task<ActionResult<Paths>> GetPaths(int id, int id2)
        {
            var paths = await _context.Paths.FirstOrDefaultAsync(a => a.pinpoint_Id == id && a.pinpoint_Id_2 == id2);
            var paths2 = await _context.Paths.FirstOrDefaultAsync(a => a.pinpoint_Id == id2 && a.pinpoint_Id_2 == id);


            if (paths == null && paths2 == null)
            {
                return NotFound();
            }
            else if (paths != null && paths2 == null)
            {
                return paths;
            }
            else
            {
                return paths2;
            }

        }

        // PUT: api/Paths/5
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPut("{id}")]
        //public async Task<IActionResult> PutPaths(int id, Paths paths)
        //{
        //    if (id != paths.pinpoint_Id)
        //    {
        //        return BadRequest();
        //    }

        //    _context.Entry(paths).State = EntityState.Modified;

        //    try
        //    {
        //        await _context.SaveChangesAsync();
        //    }
        //    catch (DbUpdateConcurrencyException)
        //    {
        //        if (!PathsExists(id))
        //        {
        //            return NotFound();
        //        }
        //        else
        //        {
        //            throw;
        //        }
        //    }

        //    return NoContent();
        //}

        // POST: api/Paths
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost]
        public async Task<ActionResult<Paths>> PostPaths(Paths paths)
        {
            _context.Paths.Add(paths);


            if (PathsExists(paths.pinpoint_Id_2, paths.pinpoint_Id))
            {
                return Conflict();
            }
            else
            {
                await _context.SaveChangesAsync();

            }


            return CreatedAtAction("GetPaths", new { id = paths.pinpoint_Id }, paths);
        }

        // DELETE: api/Paths/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeletePaths(int id)
        {
            var paths = await _context.Paths.FindAsync(id);
            if (paths == null)
            {
                return NotFound();
            }

            _context.Paths.Remove(paths);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        private bool PathsExists(int id2, int id)
        {
            bool check1 = _context.Paths.Any(e => e.pinpoint_Id == id2);
            bool check2 = _context.Paths.Any(e => e.pinpoint_Id_2 == id);

            if (check1 && check2)
            {
                return true;
            }
            else
            {
                return false;
            }


        }
    }
}
