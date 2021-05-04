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
    public class RolesController : ControllerBase
    {
        private readonly BuddyAPIContext _context;

        public RolesController(BuddyAPIContext context)
        {
            _context = context;
        }

        // GET: api/Roles
        [HttpGet ("getAllRoles")]
        public async Task<ActionResult<IEnumerable<Roles>>> GetRoles()
        {
            return await _context.Roles.ToListAsync();
        }

        // GET: api/Roles/5
        [HttpGet("getRoleById")]
        public async Task<ActionResult<Roles>> GetRoles(int id)
        {
            var roles = await _context.Roles
               //.Include(i => i.Users)
               .FirstOrDefaultAsync(i => i.Role_Id == id);

            //var roles = await _context.Roles.FindAsync(id);

            if (roles == null)
            {
                return NotFound();
            }

            return roles;
        }

        // PUT: api/Roles/5
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPut("editRoleById")]
        public async Task<IActionResult> PutRoles(int id, Roles roles)
        {
            if (id != roles.Role_Id)
            {
                return BadRequest();
            }

            _context.Entry(roles).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!RolesExists(id))
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

        // POST: api/Roles
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost("addRole")]
        public async Task<ActionResult<Roles>> PostRoles(Roles roles)
        {
            _context.Roles.Add(roles);
            await _context.SaveChangesAsync();

            return CreatedAtAction("getRoleById", new { id = roles.Role_Id }, roles);
        }

        // DELETE: api/Roles/5
        [HttpDelete("deleteRoleById")]
        public async Task<IActionResult> DeleteRoles(int id)
        {
            var roles = await _context.Roles.FindAsync(id);
            if (roles == null)
            {
                return NotFound();
            }

            _context.Roles.Remove(roles);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        

        private bool RolesExists(int id)
        {
            return _context.Roles.Any(e => e.Role_Id == id);
        }
    }
}
