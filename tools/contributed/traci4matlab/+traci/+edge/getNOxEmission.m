function NOxEmission = getNOxEmission(edgeID)
import traci.constants
%getNOxEmission Get the NOx emission in the edge.
%   NOxEmission = getNOxEmission(EDGEID) Returns the NOx emission in mg for
%   the last time step on the given edge.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

NOxEmission = traci.edge.getUniversal(constants.VAR_NOXEMISSION, edgeID);