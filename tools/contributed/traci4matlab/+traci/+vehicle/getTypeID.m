function typeID = getTypeID(vehID)
%getTypeID Returns the id of the type of the named vehicle.
%   typeID = getTypeID(VEHID) Returns the id of the type of the named 
%   vehicle.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
typeID = traci.vehicle.getUniversal(constants.VAR_TYPE, vehID);