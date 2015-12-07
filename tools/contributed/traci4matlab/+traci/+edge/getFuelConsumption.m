function fuelConsumption = getFuelConsumption(edgeID)
%getFuelConsumption Get the fuel consumption on the edge.
%   fuelConsumption = getFuelConsumption(EDGEID) Returns the fuel 
%   consumption in ml for the last time step on the given edge.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
fuelConsumption = traci.edge.getUniversal(constants.VAR_FUELCONSUMPTION, edgeID);