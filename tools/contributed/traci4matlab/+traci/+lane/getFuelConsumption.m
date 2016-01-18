function FuelConsumption = getFuelConsumption(laneID)
%getFuelConsumption Get the fuel consumption on the lane.
%   fuelConsumption = getFuelConsumption(LANEID) Returns the fuel 
%   consumption in ml for the last time step on the given lane.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getFuelConsumption.m 20 2015-03-02 16:52:32Z afacostag $

import traci.constants
FuelConsumption = traci.lane.getUniversal(constants.VAR_FUELCONSUMPTION, laneID);