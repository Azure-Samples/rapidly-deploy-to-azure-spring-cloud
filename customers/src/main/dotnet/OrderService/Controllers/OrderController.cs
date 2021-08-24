using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using OrderService.Models;

namespace OrderService.Controllers
{
    [ApiController]
    [Route("orders")]
    public class OrderController : Controller
    {
        
        [HttpGet]
		[Route("{customerId?}")]
        public List<Order> Get(int customerId)
        {
            return RandomOrderGenerator(customerId);
        }

        private List<Order> RandomOrderGenerator(int customerId)
        {
            const int Min = 0;
            const int Max = 20;
            var randNum = new Random();
            
            int[] idCollection = Enumerable
                .Repeat(0, randNum.Next(Min, Max))
                .Select(i => randNum.Next(Min, Max))
                .ToArray();

            return idCollection.Select(num => new Order { Id = num, CustomerId = customerId }).ToList();
        }
        }

    } 
    
    