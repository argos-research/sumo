function FuelConsumption = getFuelConsumption(laneID)
%getFuelConsumption Get the fuel consumption on the lane.
%   fuelConsumption = getFuelConsumption(LANEID) Returns the fuel 
%   consumption in ml for the last time step on the given lane.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getFuelConsumption.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
FuelConsumption = traci.lane.getUniversal(constants.VAR_FUELCONSUMPTION, laneID);