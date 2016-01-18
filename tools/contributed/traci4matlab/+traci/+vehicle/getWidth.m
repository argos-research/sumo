function width = getWidth(vehID)
%getWidth Returns the width in m of this vehicle.
%   width = getWidth(VEHID) Returns the width in m of this vehicle.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getWidth.m 22 2015-04-13 14:20:57Z afacostag $

import traci.constants
width = traci.vehicle.getUniversal(constants.VAR_WIDTH, vehID);